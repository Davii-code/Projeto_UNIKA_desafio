package com.projetounika;

import com.projetounika.entities.Monitorador;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class Editar extends WebPage {
    // Construtor padrão sem argumentos


    // Construtor principal
    public Editar() {
        Monitorador monitorador = new Monitorador();
        IModel<Monitorador> monitoradorIModel = new CompoundPropertyModel<>(monitorador);

        Form<Monitorador> form = new Form<>("edit", monitoradorIModel) {
            @Override
            protected void onSubmit() {
                // Lógica de envio do formulário, se necessário
            }
        };

        form.add(new TextField<>("nome"));

        add(form);
    }
}





