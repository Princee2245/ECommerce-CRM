package com.example.crmSystem.Controller;

import com.example.crmSystem.Repository.SalesMetricsRepository;
import com.example.crmSystem.Service.*;
import com.example.crmSystem.model.*;

import com.example.crmSystem.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private SalesMetricsService salesMetricsService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private EmailService emailService;



    @GetMapping("/home")
    public String home(Model model,Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        List<User> userForSalesMetrics=new LinkedList<>();
        for(SalesMetrics metrics:salesMetricsService.getAllSalesMetrics()){
            userForSalesMetrics.add(userService.findById(metrics.getUserId()));
        }
        model.addAttribute("user", user);
        model.addAttribute("metrics", salesMetricsService.getAllSalesMetrics());
        model.addAttribute("tasks", taskService.findByUserId(user.getId()));
        model.addAttribute("meetings", meetingService.findByUserId(user.getId()));
        model.addAttribute("userForSalesMetrics", userForSalesMetrics);

        return "manager/home";
    }





    @GetMapping("/tasks")
    public String showTask(Model model, Authentication authentication){
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        List<Task> tasks=taskService.findByUserId(user.getId());
        model.addAttribute("tasks",tasks);
        return "manager/tasks";
    }

    @GetMapping("/tasks/updateTask")
    public String updateTask(@RequestParam("taskId") long id, Model model){
        Task task=taskService.findById(id);
        model.addAttribute("task", task);
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        return "manager/forms/task-form";
    }

    @GetMapping("/tasks/addTask")
    public  String addTask(Model model){
        Task task=new Task();
        model.addAttribute("task",task);
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        return "manager/forms/task-form";
    }
    @PostMapping("/tasks/saveTask")
    public String saveTask(@ModelAttribute("task") Task task,Model model) {
        // Fetch the user based on the provided email
        User user = userService.findByEmail(task.getUser().getEmail());
        if (user != null) {
            task.setUser(user);
            taskService.save(task);
        } else {

            model.addAttribute("task",task);
            return "redirect:/manager/forms/task-form";
        }
        return "redirect:/manager/tasks";
    }


    @GetMapping("/tasks/deleteTask")
    public String deleteContact(@RequestParam("taskId") long id) {
        taskService.deleteById(id);
        return "redirect:/manager/tasks";
    }

    @GetMapping("/meetings")
    public String viewMeetings(Model model, Authentication authentication) {
        String email = SecurityUtil.extractEmail(authentication);
        User user = userService.findByEmail(email);
        List<Meeting> meetings = meetingService.findByUserId(user.getId());
        model.addAttribute("meetings", meetings);
        return "manager/meetings";
    }

    @GetMapping("/meetings/updateMeeting")
    public String updateMeeting(@RequestParam("meetingId") long id,Model model){


        Meeting meeting=meetingService.findById(id);
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        model.addAttribute("meeting",meeting);
        return "manager/forms/meeting-form";
    }

    @GetMapping("/meetings/addMeeting")
    public  String addMeeting(Model model){
        Meeting meeting=new Meeting();
        model.addAttribute("meeting",meeting);
        model.addAttribute("users", userService.findAll()); // Assuming userService has findAll() method
        return "manager/forms/meeting-form";
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
            return "redirect:/manager/meetings/addMeeting";
        }
        return "redirect:/manager/meetings";
    }


    @GetMapping("/meetings/deleteMeeting")
    public String deleteMeeting(@RequestParam("MeetingId") long id) {
        meetingService.deleteById(id);
        return "redirect:/manager/meetings";
    }



    @GetMapping("/customers")
    public String viewCustomers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "manager/customers";
    }
    @GetMapping("/customers/updateCustomer")
    public String updateCustomer(@RequestParam("customerId") long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("users", userService.findAll());
        return "manager/forms/customer-form";
    }

    @GetMapping("/customers/addCustomer")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("users", userService.findAll());
        return "manager/forms/customer-form";
    }

    @PostMapping("/customers/saveCustomer")
    public String saveCustomer(@ModelAttribute Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "customerForm";
        }

        customerService.save(customer);  // Save the customer
        return "redirect:/manager/customers";
    }


    @GetMapping("/customers/deleteCustomer")
    public String deleteCustomer(@RequestParam("customerId") long id) {
        customerService.deleteById(id);
        return "redirect:/manager/customers";
    }





    @GetMapping("/sales-metrics")
    public String viewSalesMetrics(Model model) {
        List<SalesMetrics> metrics = salesMetricsService.getAllSalesMetrics();
        System.out.println("Sales Metrics: " + metrics);



        List<User> users=new LinkedList<>();
        for(SalesMetrics met:metrics){
            User user=userService.findById(met.getUserId());
            users.add(user);
        }
        System.out.println(users);
        model.addAttribute("users",users);
        model.addAttribute("metrics", metrics);
        return "manager/salesMetrics";
    }


    @GetMapping("/sales")
    public String viewSales(Model model) {
        List<Sale> sales = saleService.findAll();
        model.addAttribute("sales", sales);
        return "manager/sales";
    }

    @GetMapping("/sales/updateSale")
    public String updateSale(@RequestParam("saleId") long id, Model model) {
        Sale sale = saleService.findById(id);
        model.addAttribute("sale", sale);
        model.addAttribute("users",userService.findAll());
        return "manager/forms/sale-form";
    }

    @GetMapping("/sales/addSale")
    public String addSale(Model model) {
        Sale sale = new Sale();
        model.addAttribute("sale", sale);
        model.addAttribute("users",userService.findAll());
        return "manager/forms/sale-form";
    }

    @PostMapping("/sales/saveSale")
    public String saveSale(@ModelAttribute("sale") Sale sale) {
        saleService.save(sale);
        return "redirect:/manager/sales";
    }

    @GetMapping("/sales/deleteSale")
    public String deleteSale(@RequestParam("saleId") long id) {
        saleService.deleteById(id);
        return "redirect:/manager/sales";
    }
    // Additional endpoints for other functionalities

    @GetMapping("/send-notification")
    public String sendNotification(@RequestParam("userId") long id){

        User user=userService.findById(id);
        System.out.println("user"+user);
        String to = user.getEmail();
        String subject = "Your Sales Metrics";
       String text;

        SalesMetrics salesMetrics = salesMetricsService.findByUserId(id);
        if (salesMetrics != null) {
            text = "This is a notification from the CRM system.\n\n" +
                    "Your Sales Metrics:\n" +
                    "Total Sales: " + salesMetrics.getTotalSales() + "\n" +
                    "Monthly Sales: " + salesMetrics.getMonthlySales() + "\n" +
                    "Quarterly Sales: " + salesMetrics.getQuarterlySales();

            emailService.sendSimpleMessage(to, subject, text);
        } else {
            // Handle case where sales metrics are not found for the user
             text = "This is a notification from the CRM system.\n\n" +
                    "Sales metrics data not found for your account.";
            emailService.sendSimpleMessage(to, subject, text);
        }

        emailService.sendSimpleMessage(to, subject, text);
        return "redirect:/manager/sales-metrics";
    }


}
