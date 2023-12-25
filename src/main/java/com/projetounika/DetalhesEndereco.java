package com.projetounika;

import com.projetounika.entities.Endereco;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.List;

public class DetalhesEndereco extends Panel {

    public DetalhesEndereco(CompoundPropertyModel<List<Endereco>> endereco, String id, ModalWindow modalWindow) {
        super(id);

        final ModalWindow modal = new ModalWindow("modal");
        modal.setInitialHeight(400);
        modal.setInitialWidth(800);
        add(modal);

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
                listItem.add(new Label("principal",endereco.getPrincipal() ? "Sim" : "Não") );


                listItem.add(new AjaxLink<Void>("editarLinkEndereco") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                        modal.setContent(new EditarEndereco(endereco,modal.getContentId(),modal));
                        modal.show(target);

                    }

                });
            }
        };

        add(enderecoListView);








    }

}