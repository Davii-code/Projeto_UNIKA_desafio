package com.projetounika;

import com.projetounika.entities.Endereco;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;

public class DetalhesEndereco extends WebPage {

    DetalhesEndereco(){

    }
    public DetalhesEndereco(Endereco endereco) {
        List<String> Op = Arrays.asList("Sim", "Não");
        IModel<Endereco>enderecoIModel = new CompoundPropertyModel<>(endereco);
        Form<Endereco> form = new Form<>("edit", enderecoIModel){
            @Override
            protected void onSubmit(){

            }

        };
        form.add(new TextField<>("id"));
        form.add(new TextField<>("endereco"));
        form.add(new TextField<>("cep"));
        form.add(new TextField<>("numero"));
        form.add(new TextField<>("telefone"));
        form.add(new TextField<>("cidade"));
//        form.add(new Select<>("estado"));
//        form.add(new ListMultipleChoice<>("principal", new ListModel<>()));

//        form.add(new Select<>("principal", new AbstractReadOnlyModel<>() {
//            @Override
//            public String getObject() {
//                String s = "Sim";
//                String n = "Não";
//                return endereco.getPrincipal() ? s : n;
//            }
//
//        }));
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

        form.add(escolha);
        form.add(escolhaEstado);
        add(form);
//        "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
//                "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul",
//                "Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro",
//                "Rio Grande do Norte", "Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina",
//                "São Paulo", "Sergipe", "Tocantins"
    }

}
