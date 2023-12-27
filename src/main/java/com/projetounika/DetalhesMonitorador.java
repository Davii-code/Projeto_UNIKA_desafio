package com.projetounika;

import com.projetounika.entities.Endereco;
import com.projetounika.entities.Monitorador;

import com.projetounika.services.MonitoradorHttpClient;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DetalhesMonitorador extends Panel {


    public DetalhesMonitorador(Monitorador monitorador, String id, ModalWindow modalWindow) {
        super(id);
        Endereco endereco = new Endereco();

        IModel<Monitorador> monitoradorIModel = new CompoundPropertyModel<>(monitorador);
        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");


        DropDownChoice<String> escolheTipo = new DropDownChoice<>("escolheTipo",
                Model.of(monitorador.getTipo()),
                List.of("Física", "Juridica"));

        DropDownChoice<String> escolheAtivo = new DropDownChoice<>("escolheAtivo",
                Model.of(monitorador.isAtivo() ? "Sim" : "Não"),
                List.of("Sim", "Não"));

        Form<Monitorador> form = new Form<>("edit", monitoradorIModel) {
            @Override
            protected void onSubmit() {
                String valorAtivo = escolheAtivo.getModelObject();

                if (valorAtivo.equals("Sim")){
                    monitorador.setAtivo(true);
                }else{
                    monitorador.setAtivo(false);
                }
                String dataNascimento = monitorador.getData_nascimento();
                if (!dataNascimento.contains("/") && dataNascimento.length() == 8) {
                    String dataFormatada = formatarDataComBarra(dataNascimento);
                    monitorador.setData_nascimento(dataFormatada);
                }

                String cpf = monitorador.getCpf();
               String cpfFormatado = formatarCPF(cpf);
                monitorador.setCpf(cpfFormatado);

                try {
                        monitoradorHttpClient.Atualizar(monitorador);
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
    private String formatarDataComBarra(String dataNascimento) {
        try {
            DateFormat formatoEntrada = new SimpleDateFormat("ddMMyyyy");
            DateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");
            return formatoSaida.format(formatoEntrada.parse(dataNascimento));
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao formatar a data.", e);
        }
    }
    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        // Verificar se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos numéricos.");
        }

        // Formatando o CPF com pontos e traço
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
}







