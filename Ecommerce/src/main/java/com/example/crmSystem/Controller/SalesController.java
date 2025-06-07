package com.example.crmSystem.Controller;

import com.example.crmSystem.Service.*;
import com.example.crmSystem.model.*;
import com.example.crmSystem.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SaleService saleService;


    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        model.addAttribute("tasks", taskService.findByUserId(user.getId()));
        model.addAttribute("meetings", meetingService.findByUserId(user.getId()));
        model.addAttribute("user",user);


        return "sales/home";
    }

    @GetMapping("/tasks")
    public String viewTasks(Model model, Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        List<Task> tasks = taskService.findByUserId(user.getId());
        model.addAttribute("tasks", tasks);
        return "sales/tasks";
    }

    @GetMapping("/tasks/updateTask")
    public String updatetask(@RequestParam("taskId") long id,Model model){
        Task task=taskService.findById(id);
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        model.addAttribute("task",task);
        return "sales/forms/task-form";
    }

    @GetMapping("/tasks/addTask")
    public  String addTask(Model model){
        Task task=new Task();
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        model.addAttribute("task",task);
        return "sales/forms/task-form";
    }
    @PostMapping("/tasks/saveTask")
    public String saveTask(@ModelAttribute("task") Task task,Model model) {
        // Fetch the user based on the provided email
        User user = userService.findByEmail(task.getUser().getEmail());
        if (user != null) {
            task.setUser(user);
            taskService.save(task);

        } else {
            // Handle case where user is not found
            // Optionally, add an error message and redirect to an error page
            model.addAttribute("task",task);
            return "redirect:/sales/forms/task-form";
        }
        return "redirect:/sales/tasks";
    }


    @GetMapping("/tasks/deleteTask")
    public String deleteContact(@RequestParam("taskId") long id) {
        taskService.deleteById(id);
        return "redirect:/sales/tasks";
    }


    @GetMapping("/meetings")
    public String viewMeetings(Model model, Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        List<Meeting> meetings = meetingService.findByUserId(user.getId());
        model.addAttribute("meetings", meetings);
        return "sales/meetings";
    }

    @GetMapping("/meetings/updateMeeting")
    public String updateMeeting(@RequestParam("meetingId") long id,Model model){
        Meeting meeting=meetingService.findById(id);
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        model.addAttribute("meeting",meeting);
        return "sales/forms/meeting-form";
    }

    @GetMapping("/meetings/addMeeting")
    public  String addMeeting(Model model){
        Meeting meeting=new Meeting();
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        model.addAttribute("meeting",meeting);
        return "sales/forms/meeting-form";
    }
    @PostMapping("/meetings/saveMeeting")
    public String saveMeeting(@ModelAttribute("meeting") Meeting meeting) {
        // Fetch the user based on the provided email
        User user = userService.findByEmail(meeting.getUser().getEmail());
        if (user != null) {
            meeting.setUser(user);
            meetingService.save(meeting);
        } else {
            // Handle case where user is not found
            // Optionally, add an error message and redirect to an error page
            return "redirect:/sales/meetings/addMeeting";
        }
        return "redirect:/sales/meetings";
    }


    @GetMapping("/meetings/deleteMeeting")
    public String deleteMeeting(@RequestParam("MeetingId") long id) {
        meetingService.deleteById(id);
        return "redirect:/sales/meetings";
    }


    @GetMapping("/customers")
    public String viewCustomers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "sales/customers";
    }
    @GetMapping("/customers/updateCustomer")
    public String updateCustomer(@RequestParam("customerId") long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        model.addAttribute("customer", customer);
        return "sales/forms/customer-form";
    }

    @GetMapping("/customers/addCustomer")
    public String addCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        model.addAttribute("customer", customer);
        return "sales/forms/customer-form";
    }

    @PostMapping("/customers/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/sales/customers";
    }

    @GetMapping("/customers/deleteCustomer")
    public String deleteCustomer(@RequestParam("customerId") long id) {
        customerService.deleteById(id);
        return "redirect:/sales/customers";
    }




    @GetMapping("/sales")
    public String viewSales(Model model,Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        List<Sale> sales = saleService.findByUserId(user.getId());
        model.addAttribute("sales", sales);

        return "sales/sales";
    }

    @GetMapping("/sales/updateSale")
    public String updateSale(@RequestParam("saleId") long id, Model model,Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        Sale sale = saleService.findById(id);
        model.addAttribute("sale", sale);
        model.addAttribute("user",user);
        return "sales/forms/sale-form";
    }

    @GetMapping("/sales/addSale")
    public String addSale(Model model,Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);

        Sale sale = new Sale();

        model.addAttribute("sale", sale);
        model.addAttribute("user",user);
        return "sales/forms/sale-form";
    }

    @PostMapping("/sales/saveSale")
    public String saveSale(@ModelAttribute("sale") Sale sale,Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        sale.setUser(user);
        saleService.save(sale);
        return "redirect:/sales/sales";
    }

    @GetMapping("/sales/deleteSale")
    public String deleteSale(@RequestParam("saleId") long id) {
        saleService.deleteById(id);
        return "redirect:/sales/sales";
    }







    // Additional endpoints for managing leads, etc.
}
