package com.ventas.application.views.eliminarusuario;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.ventas.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "hello", layout = MainView.class)
@PageTitle("Eliminar Usuario")
@CssImport("./styles/views/eliminarusuario/eliminar-usuario-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class EliminarUsuarioView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public EliminarUsuarioView() {
        setId("eliminar-usuario-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener( e-> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
