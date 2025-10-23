package com.empresa.crudmixto.service;

import com.empresa.crudmixto.entity.Empleado;
import com.empresa.crudmixto.entity.Proyecto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    // Exportar empleados a Excel
    public byte[] exportEmpleadosToExcel(List<Empleado> empleados) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empleados");

        // Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Apellido");
        headerRow.createCell(3).setCellValue("Cargo");
        headerRow.createCell(4).setCellValue("Email");

        // Data
        int rowNum = 1;
        for (Empleado emp : empleados) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getNombre());
            row.createCell(2).setCellValue(emp.getApellido());
            row.createCell(3).setCellValue(emp.getCargo());
            row.createCell(4).setCellValue(emp.getEmail());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    // Exportar proyectos a Excel
    public byte[] exportProyectosToExcel(List<Proyecto> proyectos) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Proyectos");

        // Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Descripción");
        headerRow.createCell(3).setCellValue("Empleado ID");

        // Data
        int rowNum = 1;
        for (Proyecto proy : proyectos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(proy.getId());
            row.createCell(1).setCellValue(proy.getNombre());
            row.createCell(2).setCellValue(proy.getDescripcion());
            row.createCell(3).setCellValue(proy.getEmpleadoId() != null ? proy.getEmpleadoId().toString() : "");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    // Exportar empleados a PDF
    public byte[] exportEmpleadosToPDF(List<Empleado> empleados) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Lista de Empleados").setFontSize(18));

        Table table = new Table(5);
        table.addHeaderCell("ID");
        table.addHeaderCell("Nombre");
        table.addHeaderCell("Apellido");
        table.addHeaderCell("Cargo");
        table.addHeaderCell("Email");

        for (Empleado emp : empleados) {
            table.addCell(String.valueOf(emp.getId()));
            table.addCell(emp.getNombre());
            table.addCell(emp.getApellido());
            table.addCell(emp.getCargo());
            table.addCell(emp.getEmail());
        }

        document.add(table);
        document.close();
        return outputStream.toByteArray();
    }

    // Exportar proyectos a PDF
    public byte[] exportProyectosToPDF(List<Proyecto> proyectos) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Lista de Proyectos").setFontSize(18));

        Table table = new Table(4);
        table.addHeaderCell("ID");
        table.addHeaderCell("Nombre");
        table.addHeaderCell("Descripción");
        table.addHeaderCell("Empleado ID");

        for (Proyecto proy : proyectos) {
            table.addCell(proy.getId());
            table.addCell(proy.getNombre());
            table.addCell(proy.getDescripcion());
            table.addCell(proy.getEmpleadoId() != null ? proy.getEmpleadoId().toString() : "");
        }

        document.add(table);
        document.close();
        return outputStream.toByteArray();
    }
}