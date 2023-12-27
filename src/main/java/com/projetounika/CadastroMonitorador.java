package com.projetounika;

import com.projetounika.entities.Monitorador;
import com.projetounika.services.MonitoradorHttpClient;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import java.io.IOException;
import java.util.List;


public class CadastroMonitorador extends Panel {
    public CadastroMonitorador(String id, ModalWindow modalWindow) {
        super(id);

        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");
        Monitorador monitorador = new Monitorador();


        DropDownChoice<String> escolheAtivo = new DropDownChoice<>("escolheAtivo",
                Model.of(),
                List.of("Sim" , "Não"));


        DropDownChoice<String> escolheTipo = new DropDownChoice<>("escolheTipo",
                Model.of(monitorador.getTipo()), // Default value
                List.of("Fisica", "Juridica"));
        Form<Monitorador> form = new Form<>("edit", new CompoundPropertyModel<>(monitorador)) {
            @Override
            protected void onSubmit() {
                String valorAtivo = escolheAtivo.getModelObject();
                String valorTipo = escolheTipo.getModelObject();
                if (valorAtivo.equals("Sim")){
                    monitorador.setAtivo(true);
                }else{
                    monitorador.setAtivo(false);
                }
                if (valorTipo.equals("Fisica")){
                    monitorador.setTipo("Fisica");
                }else {
                    monitorador.setTipo("Juridica");
                }
                try {
                    monitoradorHttpClient.Criar(monitorador);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        form.add(new TextField<>("id"));
        form.add(new TextField<>("nome"));
        form.add(new TextField<>("cpf"));
        form.add(new TextField<>("email"));
        form.add(new TextField<>("rg"));
        form.add(new TextField<>("Data_nascimento"));
        form.add(new TextField<>("inscricao"));
        form.add(new TextField<>("cnpj"));




        form.add(escolheTipo);
        form.add(escolheAtivo);
        add(form);
    }
}
