package com.projetounika;

import com.projetounika.entities.Endereco;
import com.projetounika.services.EnderecoHttpClient;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

public class DeletarEndereco extends Panel {
    public DeletarEndereco(String id, ModalWindow modalWindow, Endereco end) {
        super(id);
        Endereco endereco = new Endereco();
        EnderecoHttpClient enderecoHttpClient = new EnderecoHttpClient("http://localhost:8080/endereco");
        Form<Endereco> form = new  Form<>("edit", new CompoundPropertyModel<>(endereco)){
            @Override
            protected void onSubmit() {
                enderecoHttpClient.deletar(end.getId());
            }
        };

        add(form);

    }
}
