package com.desafioestagio.Projeto_Estagio.Services.Relatorios;


import com.desafioestagio.Projeto_Estagio.Config.ConnectionGenerico;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;



@Service
public class RelatoriosServices {

    private static String Jasper_Diretorio = "classpath:jasper/";
    private static String Jasper_PrefixoMoniPessoaFisica = "MonitoradorPessoaFisica";

    private static String Jasper_PrefixoMoniPessoaJuridica = "MonitoradorPessoaJuridica";

    private static String Jasper_PrefixoEnd = "Endereco";
    private static  String Jasper_Sufixo = ".jasper";

    @Autowired
    private Connection conn;

    private Map<String, Object> params = new HashMap<>();

    public byte[] exportaPDFMonitoradorPessoaFisica(){
        byte[] bytes = null;
        try {
            File file = ResourceUtils.getFile(Jasper_Diretorio.concat(Jasper_PrefixoMoniPessoaFisica).concat(Jasper_Sufixo));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(),params,  conn);
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }
    public byte[] exportaPDFMonitoradorPessoaJuridica(){
        byte[] bytes = null;
        try {
            File file = ResourceUtils.getFile(Jasper_Diretorio.concat(Jasper_PrefixoMoniPessoaJuridica).concat(Jasper_Sufixo));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(),params,  conn);
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
            File file = ResourceUtils.getFile(Jasper_Diretorio.concat(Jasper_PrefixoEnd).concat(Jasper_Sufixo));
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