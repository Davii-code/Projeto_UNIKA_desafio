package com.projetounika;

import com.projetounika.entities.Endereco;
import com.projetounika.services.EnderecoHttpClient;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
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

        EnderecoHttpClient enderecoHttpClient1 = new EnderecoHttpClient("http://localhost:8080/endereco/buscarCEP/");
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

        Form<Endereco> form = new Form<>("edit", new CompoundPropertyModel<>(endereco)) {

            @Override
            protected void onSubmit() {


                String ValorE = escolhaEstado.getModelObject();
                endereco.setEstado(ValorE);
                String valorPrincipal = escolhePrincipal.getModelObject();

                if (valorPrincipal != null && valorPrincipal.equals("Sim")) {
                    endereco.setPrincipal(true);
                }
                if (valorPrincipal != null && valorPrincipal.equals("Não")) {
                    endereco.setPrincipal(false);

                }


                try {
                    enderecoHttpClient.Criar(endereco, codigo);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        };


        final TextField<String> cep = new TextField<>("cep");
        final TextField<String> cod = new TextField<>("id");
        final TextField<String> end = new TextField<>("endereco");
        final TextField<String> numero = new TextField<>("numero");
        final TextField<String> bairro = new TextField<>("bairro");
        final TextField<String> telefone = new TextField<>("telefone");
        final TextField<String> cidade = new TextField<>("cidade");
        cod.setOutputMarkupId(true);
        end.setOutputMarkupId(true);
        numero.setOutputMarkupId(true);
        bairro.setOutputMarkupId(true);
        cep.setOutputMarkupId(true);
        telefone.setOutputMarkupId(true);
        cidade.setOutputMarkupId(true);
        escolhaEstado.setOutputMarkupId(true);
        cep.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                String valueCep = cep.getModelObject();
                Endereco endereco1 = enderecoHttpClient1.BuscaCep(valueCep);
                end.setModelObject(endereco1.getEndereco());
                numero.setModelObject(endereco1.getNumero());
                cidade.setModelObject(endereco1.getCidade());
                bairro.setModelObject(endereco1.getBairro());
                escolhaEstado.setModelObject(endereco1.getEstado());
                target.add(end, numero, bairro, cidade,escolhaEstado);
            }
        });





        form.add(cod);
        form.add(end);
        form.add(numero);
        form.add(bairro);
        form.add(cep);
        form.add(telefone);
        form.add(cidade);
        form.add(escolhaEstado);
        form.add(escolhePrincipal);
        add(form);


    }
}
