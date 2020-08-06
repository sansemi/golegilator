package com.tradeability.legilator.views.masterdetail;

import com.tradeability.legilator.data.entity.Person;
import com.tradeability.legilator.data.service.PersonService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;import com.tradeability.legilator.views.main.MainView;


import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@Route(value = "master-detail", layout = MainView.class)
@PageTitle("Master-Detail")
@JsModule("./src/views/masterdetail/master-detail-view.js")
@Tag("master-detail-view")
public class MasterDetailView extends PolymerTemplate<TemplateModel> {

    // This is the Java companion file of a design
    // You can find the design file in 
    // /frontend/src/views/src/views/masterdetail/master-detail-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    // Grid is created here so we can pass the class to the constructor
    private Grid<Person> grid = new Grid<>(Person.class);

    @Id
    private TextField firstName;
    @Id
    private TextField lastName;
    @Id
    private TextField email;
    @Id
    private PasswordField password;
    
    @Id
    private Button cancel;
    @Id
    private Button save;

    private Binder<Person> binder;

    public MasterDetailView(@Autowired PersonService personService) {
        setId("master-detail-view");
        grid.setColumns("firstName", "lastName", "email");
        grid.setDataProvider(new CrudServiceDataProvider<Person, Void>(personService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        // Add to the `<slot name="grid">` defined in the template
        grid.getElement().setAttribute("slot", "grid");
        getElement().appendChild(grid.getElement());
        
        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        // Configure Form
        binder = new Binder<>(Person.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);
        // note that password field isn't bound since that property doesn't exist in
        // Person

        // the grid valueChangeEvent will clear the form too
        cancel.addClickListener(e -> grid.asSingleSelect().clear());

        save.addClickListener(e -> {
            Notification.show("Not implemented");
        });
    }

    private void populateForm(Person value) {
        // Value can be null as well, that clears the form
        binder.readBean(value);

        // The password field isn't bound through the binder, so handle that
        password.setValue("");
    }
}
