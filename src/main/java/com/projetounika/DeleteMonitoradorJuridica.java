package com.projetounika;

import com.projetounika.entities.Monitorador;
import com.projetounika.services.MonitoradorHttpClient;
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


public class DeleteMonitoradorJuridica extends Panel {
    public DeleteMonitoradorJuridica(Monitorador monitorador, String id, ModalWindow modalWindow) {
        super(id);

        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");
        IModel<Monitorador> monitoradorIModel = new CompoundPropertyModel<>(monitorador);

        Form<Monitorador> form = new Form<>("edit", monitoradorIModel) {
            @Override
            protected void onSubmit() {
                monitoradorHttpClient.deletar(monitorador.getId());
            }
        };
        form.add(new TextField<>("id"));
        form.add(new TextField<>("nome"));
        form.add(new TextField<>("cnpj"));
        form.add(new TextField<>("email"));
        form.add(new TextField<>("inscricao"));
        DropDownChoice<String> escolheTipo =  new DropDownChoice<>("escolheTipo",
                Model.of(monitorador.getTipo()),
                List.of("Juridica"));

        DropDownChoice<String> escolheAtivo =  new DropDownChoice<>("escolheAtivo",
                Model.of(monitorador.isAtivo() ? "Sim" : "Não"),
                List.of("Sim", "Não"));


        form.add(escolheTipo);
        form.add(escolheAtivo);
        add(form);
    }
}
