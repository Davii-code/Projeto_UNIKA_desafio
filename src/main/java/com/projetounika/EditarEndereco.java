package com.projetounika;

import com.projetounika.JsCodigo.Mask;
import com.projetounika.entities.Endereco;
import com.projetounika.services.EnderecoHttpClient;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.IOException;
import java.util.List;

public class EditarEndereco extends Panel {
    public EditarEndereco(Endereco endereco, String id, ModalWindow modalWindow) {
        super(id);
        IModel<Endereco> enderecoIModel = new CompoundPropertyModel<>(endereco);

        EnderecoHttpClient enderecoHttpClient = new EnderecoHttpClient("http://localhost:8080/endereco");

        DropDownChoice<String> escolha = new DropDownChoice<>("principal",
                Model.of(endereco.getPrincipal() ? "Sim" : "Não"), // Valor padrão
                List.of("Sim", "Não"));


        DropDownChoice<String> escolhaEstado = new DropDownChoice<>("estado",
                Model.of(endereco.getEstado()),
                List.of("AC", "AL", "AP", "AM", "BA", "CE", "DF",
                        "ES", "GO", "MA", "MT", "MS",
                        "MG", "PA", "PB", "PR", "PE", "PI", "RJ",
                        "RN", "RS", "RO", "RR", "SC",
                        "SP", "SE", "TO"));
        final ModalWindow modal = new ModalWindow ("modal");
        modal.setInitialHeight (120);
        modal.setInitialWidth (350);
        add (modal);
        AjaxButton editarEndereco = new AjaxButton ("editarEndSubmit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit (target);
                String valorP = escolha.getModelObject();

                if (valorP.equals("Sim")){
                    endereco.setPrincipal(true);
                }else{
                    endereco.setPrincipal(false);
                }
                String ValorE = escolhaEstado.getModelObject();
                endereco.setEstado(ValorE);
                try{
                    enderecoHttpClient.Atualizar(endereco);
                    modal.setContent(new MenssagemFed(modal.getContentId(), true, "Editado com Sucesso"));
                    modal.show(target);
                    modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                        @Override
                        public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                            setResponsePage(com.projetounika.Monitorador.class);
                        }
                    });
                } catch (IOException e) {

                    modal.setContent(new MenssagemFed(modal.getContentId(), false, "Erro : " + e.getMessage()));
                    modal.show(target);
                }
            }

        };
        Form<Endereco> form = new Form<>("edit", enderecoIModel){

        };

        TextField<String> enderecoTextField = new TextField<>("endereco");
        TextField<String> cepTextField = new TextField<>("cep");
        cepTextField.add (new Mask ("99999-999"));
        TextField<String> numeroTextField = new TextField<>("numero");
        TextField<String> bairroTextField = new TextField<>("bairro");
        TextField<String> telefoneTextField = new TextField<>("telefone");
        telefoneTextField.add (new Mask ("(00) 0 0000-0000"));
        TextField<String> cidadeTextField = new TextField<>("cidade");

// Adicione os text fields ao formulário
        form.add (editarEndereco);
        form.add(enderecoTextField);
        form.add(cepTextField);
        form.add(numeroTextField);
        form.add(bairroTextField);
        form.add(telefoneTextField);
        form.add(cidadeTextField);
        form.add (escolhaEstado);
        form.add (escolha);

// Adicione o formulário à sua página (ou painel, dependendo de onde você está usando)
        add(form);






    }
}
