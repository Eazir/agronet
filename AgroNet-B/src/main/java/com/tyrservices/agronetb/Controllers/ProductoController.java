package com.tyrservices.agronetb.Controllers;

import com.tyrservices.agronetb.Models.entidades.Producto;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.CategoriaProductoCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
import com.tyrservices.agronetb.Services.ProductosService.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaProductoCrudRep categoriaService;
    private final UsuarioProductorCrudRep usuarioProductorCrudRep;

    public ProductoController(
            UsuarioProductorCrudRep usuarioProductorCrudRep,
            ProductoService productoService,
            CategoriaProductoCrudRep categoriaProductoCrudRep
    ) {
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
        this.productoService = productoService;
        this.categoriaService = categoriaProductoCrudRep;
    }

    @GetMapping("/mis-productos")
    public String misProductos(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            // Obtener productos del usuario
            List<Producto> productos = productoService.getAllByProductor(userId);
            model.addAttribute("productos", productos);
            model.addAttribute("categorias", categoriaService.findAll());

            // Datos de sesión
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userId", userId);
            model.addAttribute("userName", session.getAttribute("userName"));

        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar los productos");
        }

        return "Productor/productosP";
    }

    @GetMapping("/crear-producto")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login1";
        }

        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", session.getAttribute("userName"));

        return "Productor/crear-producto";
    }

    @PostMapping("/guardar-producto")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            // Buscar el productor existente
            Optional<UsuarioProductor> productorOpt = usuarioProductorCrudRep.findById(userId);
            if (productorOpt.isEmpty()) {
                redirectAttributes.addAttribute("error", "Productor no encontrado");
                return "redirect:/mis-productos";
            }

            // Asignar productor y estado por defecto
            producto.setDocProductor(productorOpt.get());
            producto.setEstado(true); // Producto activo por defecto

            // Guardar producto
            boolean productoGuardado = productoService.postNewProducto(producto);
            if (productoGuardado) {
                redirectAttributes.addAttribute("success", "Producto creado exitosamente");
            } else {
                redirectAttributes.addAttribute("error", "Error al guardar el producto");
            }

        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Error al crear el producto");
        }

        return "redirect:/mis-productos";
    }

    @GetMapping("/editar-producto/{id}")
    public String editarProducto(@PathVariable Long id, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Producto> productoOpt = productoService.getProductoById(id);
        if (productoOpt.isEmpty()) {
            return "redirect:/mis-productos";
        }

        // Verificar que el producto pertenece al usuario
        Producto producto = productoOpt.get();
        if (!producto.getDocProductor().getDocProductor().equals(userId)) {
            return "redirect:/mis-productos";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", session.getAttribute("userName"));

        return "Productor/editar-producto";
    }

    @PostMapping("/actualizar-producto/{id}")
    public String actualizarProducto(@PathVariable Long id,
                                     @ModelAttribute Producto producto,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            // Verificar que el producto existe y pertenece al usuario
            Optional<Producto> productoExistenteOpt = productoService.getProductoById(id);
            if (productoExistenteOpt.isEmpty()) {
                redirectAttributes.addAttribute("error", "Producto no encontrado");
                return "redirect:/mis-productos";
            }

            Producto productoExistente = productoExistenteOpt.get();
            if (!productoExistente.getDocProductor().getDocProductor().equals(userId)) {
                redirectAttributes.addAttribute("error", "No tienes permisos para editar este producto");
                return "redirect:/mis-productos";
            }

            // Actualizar producto manteniendo el productor original
            producto.setCodigoProducto(id);
            producto.setDocProductor(productoExistente.getDocProductor());

            Producto productoActualizado = productoService.upDateProducto(id, producto);

            if (productoActualizado != null) {
                redirectAttributes.addAttribute("success", "Producto actualizado exitosamente");
            } else {
                redirectAttributes.addAttribute("error", "Error al actualizar el producto");
            }

        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Error al actualizar el producto");
        }

        return "redirect:/mis-productos";
    }

    @GetMapping("/eliminar-producto/{id}")
    public String eliminarProducto(@PathVariable Long id,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            // Verificar que el producto existe y pertenece al usuario
            Optional<Producto> productoOpt = productoService.getProductoById(id);
            if (productoOpt.isEmpty()) {
                redirectAttributes.addAttribute("error", "Producto no encontrado");
                return "redirect:/mis-productos";
            }

            Producto producto = productoOpt.get();
            if (!producto.getDocProductor().getDocProductor().equals(userId)) {
                redirectAttributes.addAttribute("error", "No tienes permisos para eliminar este producto");
                return "redirect:/mis-productos";
            }

            boolean eliminado = productoService.deleteProducto(id);
            if (eliminado) {
                redirectAttributes.addAttribute("success", "Producto eliminado exitosamente");
            } else {
                redirectAttributes.addAttribute("error", "Error al eliminar el producto");
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Error al eliminar el producto");
        }

        return "redirect:/mis-productos";
    }
}