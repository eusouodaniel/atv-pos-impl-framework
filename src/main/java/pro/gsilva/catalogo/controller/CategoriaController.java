package pro.gsilva.catalogo.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value="/categorias", method= RequestMethod.GET)
    public ModelAndView getCategorias(@RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        ModelAndView mv = new ModelAndView("categorias");
        Page<Categoria> categorias = categoriaService.findAllWithPageable(PageRequest.of(currentPage - 1, pageSize));
        mv.addObject("categorias", categorias);

        int totalPages = categorias.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mv.addObject("pageNumbers", pageNumbers);
        }

        return mv;
    }

    @RequestMapping(value="/categorias/{id}", method=RequestMethod.GET)
    public ModelAndView getCategoriaDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("categoriaDetalhes");
        Categoria categoria = categoriaService.findById(id);
        mv.addObject("categoria", categoria);
        return mv;
    }

    @RequestMapping(value = "/categorias/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getCategoryFormEdit(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("categoriaForm");
        Categoria categoria = categoriaService.findById(id);
        mv.addObject("categoria", categoria);
        return mv;
    }

    @RequestMapping(value="/categorias/form", method=RequestMethod.GET)
    public String getCategoriaForm(Categoria categoria) {
        return "categoriaForm";
    }
    
    @RequestMapping(value="/categorias/form", method=RequestMethod.POST)
    public ModelAndView salvarCategoria(@Valid @ModelAttribute("categoria") Categoria categoria, 
           BindingResult result, Model model) {
        if (result.hasErrors()) {
            ModelAndView categoriaForm = new ModelAndView("categoriaForm");
            categoriaForm.addObject("mensagem", "Verifique os errors do formulário");
            return categoriaForm;
        }
        categoriaService.save(categoria);
        return new ModelAndView("redirect:/categorias");
    }

    @GetMapping("/categorias/pesquisar")
    public ModelAndView pesquisar(@RequestParam("nome") String nome) {
        ModelAndView mv = new ModelAndView("categorias");
        List<Categoria> categorias = categoriaService.findByNome(nome);
        mv.addObject("categorias", categorias);
        return mv;
    }
    
    @RequestMapping(value="/categorias/delete/{id}", method=RequestMethod.GET)
    public String delCategoria(@PathVariable("id") long id) {
        categoriaService.excluir(id);
        return "redirect:/categorias";
    }
}
