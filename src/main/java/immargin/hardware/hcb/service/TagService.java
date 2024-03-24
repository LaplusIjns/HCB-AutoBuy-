package immargin.hardware.hcb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.DTO.TagnameDTO;
import immargin.hardware.hcb.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;
    
//  Autobuy
    //搜尋產品ID找到TAG
    public List<TagnameDTO> SearchbyProdname(String name){
        return tagRepository.findByProdName(name);
    }
    //搜尋tag找id
    public List<MaintableDTO> SearchbyTagname(String name,Integer page,Integer size){
        Pageable pageable=PageRequest.of(page, size);
        return tagRepository.findByTagName(name,pageable).getContent();
    }
    //搜尋tag總元素
    public Page<MaintableDTO> AutobuyTagPage(String name,Integer page,Integer size){
        Pageable pageable=PageRequest.of(page, size);
        return tagRepository.findByTagName(name, pageable);
    }
    public Optional<TagnameDTO> getTagname(String name){
        Optional<TagnameDTO> result = null;
        result = tagRepository.findTagnameByfk_tag(name);
        return result;
    }
//    Sinya
  //搜尋產品ID找到TAG
    public List<TagnameDTO> SinyaSearchbyProdname(String name){
        return tagRepository.SinyafindByProdName(name);
    }
    //搜尋tag找id
    public List<MaintableDTO> SinyaSearchbyTagname(String name,Integer page,Integer size){
        Pageable pageable=PageRequest.of(page, size);
        return tagRepository.SinyafindByTagName(name,pageable).getContent();
    }

  //搜尋tag總元素
    public Page<MaintableDTO> SinyaTagPage(String name,Integer page,Integer size){
        Pageable pageable=PageRequest.of(page, size);
        return tagRepository.SinyafindByTagName(name, pageable);
    }
    
    
    public Optional<TagnameDTO> SinyagetTagname(String name){
        return tagRepository.SinyafindTagnameByfk_tag(name);
    }
}
