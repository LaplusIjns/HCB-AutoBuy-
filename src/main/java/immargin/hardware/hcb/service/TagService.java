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
        List<TagnameDTO> result = null;
        result = tagRepository.findByProdName(name);
        return result;
    }
    //搜尋tag找id
    public List<MaintableDTO> SearchbyTagname(String name,Integer page,Integer size){
        List<MaintableDTO> result = null;
        Pageable pageable=PageRequest.of(page, size);
        result = tagRepository.findByTagName(name,pageable).getContent();
        return result;
    }
    //搜尋tag總元素
    public Page<MaintableDTO> AutobuyTagPage(String name,Integer page,Integer size){
        Page<MaintableDTO> pageResult = null;
        Pageable pageable=PageRequest.of(page, size);
        pageResult = tagRepository.findByTagName(name, pageable);
        return pageResult;
    }
    public Optional<TagnameDTO> getTagname(String name){
        Optional<TagnameDTO> result = null;
        result = tagRepository.findTagnameByfk_tag(name);
        return result;
    }
//    Sinya
  //搜尋產品ID找到TAG
    public List<TagnameDTO> SinyaSearchbyProdname(String name){
        List<TagnameDTO> result = null;
        result = tagRepository.SinyafindByProdName(name);
        return result;
    }
    //搜尋tag找id
    public List<MaintableDTO> SinyaSearchbyTagname(String name,Integer page,Integer size){
        List<MaintableDTO> result = null;
        Pageable pageable=PageRequest.of(page, size);
        result = tagRepository.SinyafindByTagName(name,pageable).getContent();
        return result;
    }

  //搜尋tag總元素
    public Page<MaintableDTO> SinyaTagPage(String name,Integer page,Integer size){
        Page<MaintableDTO> pageResult = null;
        Pageable pageable=PageRequest.of(page, size);
        pageResult = tagRepository.SinyafindByTagName(name, pageable);
        return pageResult;
    }
    
    
    public Optional<TagnameDTO> SinyagetTagname(String name){
        Optional<TagnameDTO> result = null;
        result = tagRepository.SinyafindTagnameByfk_tag(name);
        return result;
    }
}
