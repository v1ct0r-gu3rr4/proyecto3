package com.ventas.application.views.eliminarproducto;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.ventas.application.views.main.MainView;

@Route(value = "hello1", layout = MainView.class)
@PageTitle("Eliminar producto")
@CssImport("./styles/views/eliminarproducto/eliminarproducto-view.css")
public class EliminarproductoView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public EliminarproductoView() {
        setId("eliminarproducto-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener( e-> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
