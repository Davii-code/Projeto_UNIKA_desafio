package com.desafioestagio.Projeto_Estagio.Services.Relatorios;


import com.desafioestagio.Projeto_Estagio.Config.ConnectionGenerico;
import com.desafioestagio.Projeto_Estagio.Services.MonitoradorServices;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Collections;
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

    @Autowired
    private Connection conn;

    private Map<String, Object> params = new HashMap<>();

//    public void addParams (String key, Object value){
//        this.params.put(key,value);
//    }
//    public byte[] exportaPDFMonitoradorPorNome(String nome) {
//        List<Monitorador> monitoradores = services.findByNome(nome);
//        return exportaPDFMonitorador();
//    }
//
//    public byte[] exportaPDFMonitoradorPorCnpj(String cnpj) {
//        Monitorador monitorador = services.findBycnpj(cnpj);
//        return gerarRelatorioPDF(Collections.singletonList(monitorador));
//    }
//
//    public byte[] exportaPDFMonitoradorPorCpf(String cpf) {
//        Monitorador monitorador = services.findByCpf(cpf);
//        return gerarRelatorioPDF(Collections.singletonList(monitorador));
//    }
//
//    public byte[] exportaPDFMonitoradorPorId(Long id) {
//        Monitorador monitorador = services.findById(id);
//        return gerarRelatorioPDF(Collections.singletonList(monitorador));
//    }
//    private byte[] gerarRelatorioPDF(List<Monitorador> monitoradores) {
//        byte[] bytes = null;
//        try {
//            File file = ResourceUtils.getFile(Jasper_Diretorio.concat(Jasper_PrefixoMoni).concat(Jasper_Sufixo));
//
//            // Lógica para preencher o relatório com os dados do monitorador
//            JasperPrint print = preencherRelatorio(file, monitoradores);
//
//            bytes = JasperExportManager.exportReportToPdf(print);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (JRException e) {
//            throw new RuntimeException(e);
//        }
//        return bytes;
//    }
//
//    private JasperPrint preencherRelatorio(File file, List<Monitorador> monitoradores) throws JRException {
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("monitoradores", new JRBeanCollectionDataSource(monitoradores));
//
//        return JasperFillManager.fillReport(file.getAbsolutePath(), params, conn);
//    }

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

    public byte[] exportaPDFEndereco(){
        byte[] bytes = null;
        try {
            File file = ResourceUtils.getFile(Jasper_Diretorio.concat(Jasper_PrefixoEndereco).concat(Jasper_Sufixo));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(),params,  conn);
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }





}