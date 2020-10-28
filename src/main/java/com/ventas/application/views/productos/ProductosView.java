package com.ventas.application.views.productos;
import com.vaadin.annotations.Theme;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.ventas.application.model.Producto;
import com.ventas.application.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.ventas.application.views.main.MainView;
@Route(value = "producto", layout = MainView.class)
@PageTitle("Productos")
@CssImport(value = "./styles/views/productos/productos-view.css", include="lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class ProductosView {

    @Autowired
    private ProductoService productoService;
    private Producto producto;

    private Grid grid = new Grid();
    private TextField tipo = new TextField("Tipo");
    private TextField precio = new TextField("Precio");
    private TextField cantidad = new TextField("Cantidad");
    private Button save = new Button("Guardar", e -> saveProducto());
// //
    private GridPro<Producto> grid;
    private ListDataProvider<Producto> dataProvider;
    private Grid.Column<Producto> idColumn;
    private Grid.Column<Producto> tipoColumn;
    private Grid.Column<Producto> precioColumn;
    private Grid.Column<Producto> cantidadColumn;


    public ProductosView() {
        setId("productos-view");
        setSizeFull();
        createGrid();
        add(grid);
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        addFiltersToGrid();
    }


    private void createGridComponent() {
        grid = new GridPro<>();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        dataProvider = new ListDataProvider<Producto>(productoService.findAll());
        grid.setDataProvider(dataProvider);
    }

    private void addColumnsToGrid() {
        createIdColumn();
        createClientColumn();
        createAmountColumn();
        createStatusColumn();
        createDateColumn();
    }

    private void createIdColumn() {
        idColumn = grid.addColumn(producto.getId(), "id").setHeader("ID")
                .setWidth("120px").setFlexGrow(0);
    }

    private void createClientColumn() {
        clientColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(Alignment.CENTER);
            Image img = new Image(client.getImg(), "");
            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getClient());
            hl.add(img, span);
            return hl;
        })).setComparator(client -> client.getClient()).setHeader("Client");
    }

    private void createAmountColumn() {
        amountColumn = grid
                .addEditColumn(Client::getAmount,
                        new NumberRenderer<>(client -> client.getAmount(),
                                NumberFormat.getCurrencyInstance(Locale.US)))
                .text((item, newValue) -> item
                        .setAmount(Double.parseDouble(newValue)))
                .setComparator(client -> client.getAmount())
                .setHeader("Amount");
    }

    private void createStatusColumn() {
        statusColumn = grid.addEditColumn(Client::getClient,
                new ComponentRenderer<>(client -> {
                    Span span = new Span();
                    span.setText(client.getStatus());
                    span.getElement().setAttribute("theme",
                            "badge " + client.getStatus().toLowerCase());
                    return span;
                }))
                .select((item, newValue) -> item.setStatus(newValue),
                        Arrays.asList("Pending", "Success", "Error"))
                .setComparator(client -> client.getStatus())
                .setHeader("Status");
    }

    private void createDateColumn() {
        dateColumn = grid
                .addColumn(new LocalDateRenderer<>(
                        client -> LocalDate.parse(client.getDate()),
                        DateTimeFormatter.ofPattern("M/d/yyyy")))
                .setComparator(client -> client.getDate()).setHeader("Date")
                .setWidth("180px").setFlexGrow(0);
    }


    //
    private void updateGrid() {
        List<Company> companies = service.findAll();
        grid.setContainerDataSource(
                new BeanItemContainer<>(Company.class, companies));
        setFormVisible(false);
    }

    private void updateForm() {
        setFormVisible(!grid.getSelectedRows().isEmpty());
        if (!grid.getSelectedRows().isEmpty()) {
            company = (Company) grid.getSelectedRow();
            BeanFieldGroup.bindFieldsUnbuffered(company, this);
        }
    }

    private void setFormVisible(boolean visible) {
        name.setVisible(visible);
        website.setVisible(visible);
        save.setVisible(visible);
    }

    private void saveProducto() {
        productoService.update(producto);
        updateGrid();
    }
}
