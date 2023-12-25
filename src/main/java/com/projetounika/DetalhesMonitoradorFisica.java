package com.projetounika;

import com.projetounika.entities.Endereco;
import com.projetounika.entities.Monitorador;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

public class DetalhesMonitoradorFisica extends Panel {




    public DetalhesMonitoradorFisica(Monitorador monitorador, String id, ModalWindow modalWindow ) {
        super(id);
        Endereco endereco = new Endereco();
        IModel<Monitorador> monitoradorIModel = new CompoundPropertyModel<>(monitorador);

        Form<Monitorador> form = new Form<>("edit", monitoradorIModel) {
            @Override
            protected void onSubmit() {
            }
        };
        form.add(new TextField<>("id"));
        form.add(new TextField<>("nome"));
        form.add(new TextField<>("cpf"));
        form.add(new TextField<>("email"));
        form.add(new TextField<>("rg"));
        form.add(new DateTextField("Data_nascimento", String.valueOf(monitoradorIModel.getObject().getData_nascimento())));

        DropDownChoice<String> escolheTipo =  new DropDownChoice<>("escolheTipo",
                Model.of(monitorador.getTipo()),
                List.of("Física"));

        DropDownChoice<String> escolheAtivo =  new DropDownChoice<>("escolheAtivo",
                Model.of(monitorador.isAtivo() ? "Sim" : "Não"),
                List.of("Sim", "Não"));


        form.add(escolheTipo);
        form.add(escolheAtivo);
        add(form);

    }
}





