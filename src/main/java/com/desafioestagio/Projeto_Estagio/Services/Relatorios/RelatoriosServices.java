package com.desafioestagio.Projeto_Estagio.Services.Relatorios;


import com.desafioestagio.Projeto_Estagio.Services.MonitoradorServices;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class RelatoriosServices {

    private static String Jasper_Diretorio = "classpath:jasper/";
    private static String Jasper_PrefixoMoni= "Monitorador";

    private static String Jasper_PrefixoEndereco = "Endereco";
    private static  String Jasper_Sufixo = ".jasper";

    @Autowired
    private MonitoradorServices services;



    private Map<String, Object> params = new HashMap<>();



    public byte[] exportaPDFMonitorador(List<Monitorador> monitoradores){
        byte[] bytes = null;

        params.put("nome",monitoradores);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(monitoradores);
        try {
            File file = ResourceUtils.getFile(Jasper_Diretorio.concat(Jasper_PrefixoMoni).concat(Jasper_Sufixo));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(),params,dataSource);

            System.out.println(print);
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public byte[] exportaPDFEndereco(List<Endereco> enderecos){
        byte[] bytes = null;

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(enderecos);
        params.put("dados", enderecos);

        try {
            File file = ResourceUtils.getFile(Jasper_Diretorio.concat(Jasper_PrefixoEndereco).concat(Jasper_Sufixo));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(),params,  jrBeanCollectionDataSource);
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }





}