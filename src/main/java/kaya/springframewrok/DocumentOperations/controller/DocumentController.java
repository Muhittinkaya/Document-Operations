package kaya.springframewrok.DocumentOperations.controller;

import kaya.springframewrok.DocumentOperations.model.Document;
import kaya.springframewrok.DocumentOperations.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.print.Doc;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class DocumentController  {

    @Autowired
    DocumentRepository documentRepository;

    @RequestMapping("/displayUpload")
    public String displayUpload(ModelMap modelMap){
        List<Document> documents = documentRepository.findAll();
        System.out.println(documents.size());
        modelMap.addAttribute("documents", documents);
        return "/documentUpload";
    }

    @PostMapping(value="/upload")
    public String uploadDocument(@RequestParam("document") MultipartFile multipartFile, @RequestParam("id") long id, ModelMap modelMap){
        Document document = new Document();
        document.setId(id);
        document.setName(multipartFile.getOriginalFilename());
        try{
            document.setData(multipartFile.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
        documentRepository.save(document);

        List<Document> documents = documentRepository.findAll();
        System.out.println(documents.size());
        modelMap.addAttribute("documents", documents);
        return "/documentUpload";
    }

    @RequestMapping("/download")
    public StreamingResponseBody download(@RequestParam("id") long id, HttpServletResponse response){
        Document document = documentRepository.getById(id);
        byte[] data = document.getData();

        response.setHeader("Content-Disposition","attachment;filename-downloaded.sql");


//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename = "+document.getData().toString();

        return outputStream -> {
          outputStream.write(data);
        };
    }
}
