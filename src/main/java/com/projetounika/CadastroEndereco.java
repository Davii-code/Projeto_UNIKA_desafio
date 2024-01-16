package com.projetounika;

import com.projetounika.JsCodigo.Mask;
import com.projetounika.entities.Endereco;
import com.projetounika.services.EnderecoHttpClient;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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

import static org.apache.commons.lang.StringUtils.isEmpty;

public class CadastroEndereco extends Panel {


    public CadastroEndereco(String id, ModalWindow modalWindow, Long codigo) {
        super (id);
        Endereco endereco = new Endereco ();
        EnderecoHttpClient enderecoHttpClient = new EnderecoHttpClient ("http://localhost:8080/monitorador");

        EnderecoHttpClient enderecoHttpClient1 = new EnderecoHttpClient ("http://localhost:8080/endereco/buscarCEP/");
        DropDownChoice<String> escolhePrincipal = new DropDownChoice<> ("escolhePrincipal",
                Model.of (),
                List.of ("Sim", "Não"));

        DropDownChoice<String> escolhaEstado = new DropDownChoice<> ("estado",
                Model.of (endereco.getEstado ()),
                List.of ("AC", "AL", "AP", "AM", "BA", "CE", "DF",
                        "ES", "GO", "MA", "MT", "MS",
                        "MG", "PA", "PB", "PR", "PE", "PI", "RJ",
                        "RN", "RS", "RO", "RR", "SC",
                        "SP", "SE", "TO"));

        final TextField<String> cep = new TextField<> ("cep");
        cep.add (new Mask ("99999-999"));
        final TextField<String> end = new TextField<> ("endereco");
        final TextField<String> numero = new TextField<> ("numero");
        final TextField<String> bairro = new TextField<> ("bairro");
        final TextField<String> telefone = new TextField<> ("telefone");
        telefone.add (new Mask ("(00) 0 0000-0000"));
        final TextField<String> cidade = new TextField<> ("cidade");


        final ModalWindow modal = new ModalWindow ("modal");
        modal.setInitialHeight (120);
        modal.setInitialWidth (350);
        add (modal);
        AjaxButton cadastroEndereco = new AjaxButton ("CadastroEnd") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit (target);
                String valorEstado = escolhaEstado.getModelObject ();
                String valorCep = cep.getModelObject ();
                String valorEndereco = end.getModelObject ();
                String valorNumero = numero.getModelObject ();
                String valorBairro = bairro.getModelObject ();
                String valorTelefone = telefone.getModelObject ();
                String valorCidade = cidade.getModelObject ();
                String ValorE = escolhaEstado.getModelObject ();
                String valorPrincipal = escolhePrincipal.getModelObject ();


                if (isEmpty (valorEstado) || isEmpty (valorPrincipal) || isEmpty (valorCep) ||
                        isEmpty (valorEndereco) || isEmpty (valorNumero) ||
                        isEmpty (valorBairro) || isEmpty (valorTelefone) || isEmpty (valorCidade)) {
                    modal.setContent (new MenssagemFed (modal.getContentId (), false, "Preencha os Campos"));
                    modal.show (target);
                } else {
                    endereco.setEstado (ValorE);

                    if (valorPrincipal != null && valorPrincipal.equals ("Sim")) {
                        endereco.setPrincipal (true);
                    }
                    if (valorPrincipal != null && valorPrincipal.equals ("Não")) {
                        endereco.setPrincipal (false);

                    }


                    try {
                        enderecoHttpClient.Criar (endereco, codigo);
                        modal.setContent (new MenssagemFed (modal.getContentId (), true, "Cadastrado com Sucesso"));
                        modal.show (target);
                        modal.setWindowClosedCallback (new ModalWindow.WindowClosedCallback () {
                            @Override
                            public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                                setResponsePage (com.projetounika.Monitorador.class);
                            }
                        });
                    } catch (IOException e) {
                        modal.setContent (new MenssagemFed (modal.getContentId (), false, "Erro ao cadastrar: " + e.getMessage ()));
                        modal.show (target);
                    }
                }
            }


        };
        Form<Endereco> form = new Form<> ("edit", new CompoundPropertyModel<> (endereco)) {
        };


        end.setOutputMarkupId (true);
        numero.setOutputMarkupId (true);
        bairro.setOutputMarkupId (true);
        cep.setOutputMarkupId (true);
        telefone.setOutputMarkupId (true);
        cidade.setOutputMarkupId (true);
        escolhaEstado.setOutputMarkupId (true);
        cep.add (new OnChangeAjaxBehavior () {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                String valueCep = cep.getModelObject ();
                Endereco endereco1 = enderecoHttpClient1.BuscaCep (valueCep);
                end.setModelObject (endereco1.getEndereco ());
                numero.setModelObject (endereco1.getNumero ());
                cidade.setModelObject (endereco1.getCidade ());
                bairro.setModelObject (endereco1.getBairro ());
                escolhaEstado.setModelObject (endereco1.getEstado ());
                target.add (end, numero, bairro, cidade, escolhaEstado);
            }
        });


        form.add (cadastroEndereco);
        form.add (end);
        form.add (numero);
        form.add (bairro);
        form.add (cep);
        form.add (telefone);
        form.add (cidade);
        form.add (escolhaEstado);
        form.add (escolhePrincipal);
        add (form);


    }
}
