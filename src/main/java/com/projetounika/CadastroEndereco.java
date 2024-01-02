package com.projetounika;

import com.projetounika.entities.Endereco;
import com.projetounika.services.EnderecoHttpClient;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import java.io.IOException;
import java.util.List;

public class CadastroEndereco extends Panel {
    public CadastroEndereco(String id, ModalWindow modalWindow, Long codigo) {
        super(id);
        Endereco endereco = new Endereco();
        EnderecoHttpClient enderecoHttpClient = new EnderecoHttpClient("http://localhost:8080/monitorador");

        DropDownChoice<String> escolhePrincipal = new DropDownChoice<>("escolhePrincipal",
                Model.of(),
                List.of("Sim", "Não"));

        DropDownChoice<String> escolhaEstado = new DropDownChoice<>("estado",
                Model.of(endereco.getEstado()),
                List.of("AC", "AL", "AP", "AM", "BA", "CE", "DF",
                        "ES", "GO", "MA", "MT", "MS",
                        "MG", "PA", "PB", "PR", "PE", "PI", "RJ",
                        "RN", "RS", "RO", "RR", "SC",
                        "SP", "SE", "TO"));
        Form<Endereco>form = new Form<>("edit", new CompoundPropertyModel<>(endereco)){

            @Override
            protected void onSubmit() {
                String valorPrincipal = escolhePrincipal.getModelObject();
                if (valorPrincipal.equals("Sim")) {
                  endereco.setPrincipal(true);
                } else {
                    endereco.setPrincipal(false);
                }

                String ValorE = escolhaEstado.getModelObject();
                endereco.setEstado(ValorE);
                try{
                    enderecoHttpClient.Criar(endereco,codigo);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

        };

        final TextField<String> cod = new TextField<>("id");
        final TextField<String> end = new TextField<>("endereco");
        final TextField<String> numero = new TextField<>("numero");
        final TextField<String> cep = new TextField<>("cep");
        final TextField<String> telefone = new TextField<>("telefone");
        final TextField<String> cidade = new TextField<>("cidade");



        form.add(cod);
        form.add(end);
        form.add(numero);
        form.add(cep);
        form.add(telefone);
        form.add(cidade);
        form.add(escolhaEstado);
        form.add(escolhePrincipal);
        add(form);


    }
}
