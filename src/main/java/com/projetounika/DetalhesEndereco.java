package com.projetounika;

import com.projetounika.entities.Endereco;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.List;

public class DetalhesEndereco extends Panel {

    public DetalhesEndereco(CompoundPropertyModel<List<Endereco>> endereco, String id, ModalWindow modalWindow, Long codigo) {
        super(id);

        final ModalWindow modal = new ModalWindow("modal");
        modal.setInitialHeight(450);
        modal.setInitialWidth(800);
        add(modal);

        Endereco endereco1 = new Endereco();

        final ListView<Endereco> enderecoListView = new ListView<Endereco>("end",endereco) {
            @Override
            protected void populateItem(ListItem<Endereco> listItem) {
                final Endereco endereco = listItem.getModelObject();
                listItem.add(new Label("id",endereco.getId()));
                listItem.add(new Label("en",endereco.getEndereco()));
                listItem.add(new Label("num",endereco.getNumero()));
                listItem.add(new Label("cep",endereco.getCep()));
                listItem.add(new Label("est",endereco.getEstado()));
                listItem.add(new Label("cid",endereco.getCidade()));
                listItem.add(new Label("bairro",endereco.getBairro()));
                listItem.add(new Label("principal",endereco.getPrincipal() ? "Sim" : "Não") );


                listItem.add(new AjaxLink<Void>("editarLinkEndereco") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                        modal.setContent(new EditarEndereco(endereco,modal.getContentId(),modal));
                        modal.show(target);

                    }

                });
                listItem.add( new AjaxLink<Void>("linkdeletar") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        modal.setInitialHeight(273);
                        modal.setInitialWidth(518);
                        modal.setContent(new DeletarEndereco(modal.getContentId(),modal,endereco));
                        modal.show(ajaxRequestTarget);
                    }
                });



            }
        };


        AjaxLink<Void> linkCadastro = new AjaxLink<>("linkCadastroEnd") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                modal.setContent(new CadastroEndereco(modal.getContentId(),modal,codigo));
                modal.show(ajaxRequestTarget);
            }
        };
        ExternalLink linkPdf = new ExternalLink("pdf","http://localhost:8080/endereco/relatorio/pdfs?id=" +codigo);
        add(linkPdf);

        ExternalLink linkExcel = new ExternalLink ("excel","http://localhost:8080/endereco/relatorio/excel?id=" +codigo);
        add(linkExcel);

        add(linkCadastro);
        add(enderecoListView);

    }

}
