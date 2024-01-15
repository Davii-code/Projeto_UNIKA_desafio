package com.projetounika;

import com.projetounika.JsCodigo.Mask;
import com.projetounika.JsCodigo.ValidacaoInput;
import com.projetounika.entities.Monitorador;
import com.projetounika.services.MonitoradorHttpClient;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class CadastroMonitorador extends Panel implements IFeedback {

    public CadastroMonitorador(String id, ModalWindow modalWindow, String valorTipo) {
        super (id);
        Validacao validacao = new Validacao ();
        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient ("http://localhost:8080/monitorador");
        Monitorador monitorador = new Monitorador ();
        DropDownChoice<String> escolheAtivo = new DropDownChoice<> ("escolheAtivo",
                Model.of (),
                List.of ("Sim", "Não"));

        final ModalWindow modal = new ModalWindow ("modal");
        modal.setInitialHeight (120);
        modal.setInitialWidth (350);
        add (modal);
        final TextField<String> codigo = new TextField<> ("id");

        final TextField<String> cpf = new TextField<> ("cpf");
        cpf.add (new Mask ("000.000.000-00"));
        final TextField<String> cnpj = new TextField<> ("cnpj");
        cnpj.add (new Mask ("00.000.000/0000-00"));
        final TextField<String> nome = new TextField<> ("nome");
        final TextField<String> email = new TextField<> ("email");
        email.add (EmailAddressValidator.getInstance ());
        final TextField<String> rg = new TextField<> ("rg");
        rg.add (new ValidacaoInput ());
        final TextField<String> data = new TextField<> ("Data_nascimento");
        data.add (new Mask ("00/00/0000"));
        final TextField<String> inscricao = new TextField<> ("inscricao");
        inscricao.add (new ValidacaoInput ());

        //Label
        final Label IE = new Label ("ins", "Inscrição Estadual");
        final Label RG = new Label ("RG", "RG");
        final Label date = new Label ("date", "Data de Nascimento");


        AjaxButton submitBehavior = new AjaxButton("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);

                String valorAtivo = escolheAtivo.getModelObject();
                String Fcpf = cpf.getModelObject();
                String cnpjFormatado = cnpj.getModelObject();
                String nomeValor = nome.getModelObject();
                String emailValor = email.getModelObject();
                String rgValor = rg.getModelObject();
                String fData = data.getModelObject();
                String ValorInscricao = inscricao.getModelObject();

                // Verifique se algum campo obrigatório está vazio de acordo com o tipo de pessoa
                if (nomeValor == null || emailValor == null || (valorTipo.equals("Fisica") && (Fcpf == null || Fcpf.trim().isEmpty() || rgValor == null || rgValor.trim().isEmpty() || fData == null || fData.trim().isEmpty())) ||
                        (valorTipo.equals("Juridica") && (cnpjFormatado == null || cnpjFormatado.trim().isEmpty() || ValorInscricao == null || ValorInscricao.trim().isEmpty()))) {
                    modal.setContent(new MenssagemFed(modal.getContentId(), false, "Preencha todos os campos obrigatórios"));
                    modal.show(target);
                } else {
                    try {
                        // Configuração do monitorador
                        if ("Sim".equals(valorAtivo)) {
                            monitorador.setAtivo(true);
                        } else {
                            monitorador.setAtivo(false);
                        }
                        monitorador.setTipo(valorTipo);

                        if ("Fisica".equals(monitorador.getTipo())) {
                            monitorador.setCpf(formatarCPF(Fcpf));
                            fData = fData.replaceAll("\\D", "");
                            monitorador.setData_nascimento(formatarDataComBarra(fData));
                        } else {
                            cnpjFormatado = cnpjFormatado.replaceAll("\\D", "");
                            monitorador.setCnpj(cnpjFormatado);
                        }

                        // Validar CPF
                        if (Fcpf != null) {
                            if (!validacao.isCPF(Fcpf)) {
                                throw new Exception("Erro ao cadastrar, verificar o CPF");
                            }
                        }

                        // Validar CNPJ
                        if (cnpjFormatado != null) {
                            if (!validacao.isCNPJ(cnpjFormatado)) {
                                throw new Exception("Erro ao cadastrar, verificar o CNPJ");
                            }
                        }

                        // Criação do monitorador
                        monitoradorHttpClient.Criar(monitorador);

                        // Exibição de sucesso
                        modal.setContent(new MenssagemFed(modal.getContentId(), true, "Cadastrado com Sucesso"));
                        modal.show(target);
                        modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                            @Override
                            public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                                setResponsePage(com.projetounika.Monitorador.class);
                            }
                        });
                    } catch (Exception e) {
                        // Exibição de erro
                        modal.setContent(new MenssagemFed(modal.getContentId(), false, "Erro ao cadastrar: " + e.getMessage()));
                        modal.show(target);
                    }
                }
            }
        };


        Form<Monitorador> form = new Form<> ("edit", new CompoundPropertyModel<> (monitorador)) {
        };

        add (form);

        form.add (submitBehavior);
        if (valorTipo.equals ("Fisica")) {
            cpf.setVisible (true);
            cpf.setOutputMarkupPlaceholderTag (true);
            rg.setVisible (true);
            rg.setOutputMarkupPlaceholderTag (true);
            data.setVisible (true);
            data.setOutputMarkupPlaceholderTag (true);
            cnpj.setVisible (false);
            cnpj.setOutputMarkupPlaceholderTag (true);
            inscricao.setVisible (false);
            inscricao.setOutputMarkupPlaceholderTag (true);
            IE.setVisible (false);
            IE.setOutputMarkupPlaceholderTag (true);
            RG.setVisible (true);
            RG.setOutputMarkupPlaceholderTag (true);
            date.setVisible (true);
            date.setOutputMarkupPlaceholderTag (true);
        } else {
            if (valorTipo.equals ("Juridica")) {
                cnpj.setVisible (true);
                cnpj.setOutputMarkupPlaceholderTag (true);
                inscricao.setVisible (true);
                inscricao.setOutputMarkupPlaceholderTag (true);
                IE.setVisible (true);
                IE.setOutputMarkupPlaceholderTag (true);
                RG.setVisible (false);
                RG.setOutputMarkupPlaceholderTag (true);
                cpf.setVisible (false);
                cpf.setOutputMarkupPlaceholderTag (true);
                rg.setVisible (false);
                rg.setOutputMarkupPlaceholderTag (true);
                data.setVisible (false);
                data.setOutputMarkupPlaceholderTag (true);
                date.setVisible (false);
                date.setOutputMarkupPlaceholderTag (true);
            }
        }

        form.add (codigo);
        form.add (nome);
        form.add (cpf);
        form.add (cnpj);
        form.add (email);
        form.add (rg);
        form.add (data);
        form.add (inscricao);
        form.add (IE);
        form.add (date);
        form.add (RG);
        form.add (escolheAtivo);


    }


    private String formatarDataComBarra(String dataNascimento) {
        try {
            DateFormat formatoEntrada = new SimpleDateFormat ("ddMMyyyy");
            DateFormat formatoSaida = new SimpleDateFormat ("dd/MM/yyyy");
            return formatoSaida.format (formatoEntrada.parse (dataNascimento));
        } catch (ParseException e) {
            throw new RuntimeException ("Erro ao formatar a data.", e);
        }
    }

    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll ("[^0-9]", "");
        // Verificar se o CPF tem 11 dígitos
        if (cpf.length () != 11) {
            throw new IllegalArgumentException ("O CPF deve conter 11 dígitos numéricos.");
        }

        // Formatando o CPF com pontos e traço
        return cpf.substring (0, 3) + "." + cpf.substring (3, 6) + "." +
                cpf.substring (6, 9) + "-" + cpf.substring (9, 11);
    }

}