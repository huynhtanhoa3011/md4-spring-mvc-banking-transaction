package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    public ICustomerService customerService;

    @GetMapping
    public ModelAndView listCustomers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/list");
        Iterable<Customer> customers = customerService.findALl();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("success", null);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView save(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        customer.setBalance(new BigDecimal(0));
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("success", "successfully add new customer");
        return modelAndView;
    }

    @GetMapping("/customer/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit/{id}")
    private ModelAndView updateCustomer(@PathVariable Long id, @Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        customer.setBalance(new BigDecimal(0));
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer",customer);
        modelAndView.addObject("success","Customer updated successfully");
        return modelAndView;
    }

    @GetMapping("/customer/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }
}
