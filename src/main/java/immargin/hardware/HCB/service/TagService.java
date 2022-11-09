package immargin.hardware.HCB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import immargin.hardware.HCB.DTO.MaintableDTO;
import immargin.hardware.HCB.DTO.TagnameDTO;
import immargin.hardware.HCB.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;
    
    
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
    public int[] gettagtotal(String name,Integer page,Integer size){
        Page<MaintableDTO> pageResult = null;
        int[] abc= new int[2];
        Pageable pageable=PageRequest.of(page, size);
        pageResult = tagRepository.findByTagName(name, pageable);
        abc[0] = pageResult.getTotalPages();
        abc[1] = (int) pageResult.getTotalElements();
        
        return abc;
    }
    public Optional<TagnameDTO> getTagname(String name){
        Optional<TagnameDTO> result = null;
        result = tagRepository.findTagnameByfk_tag(name);
        if(result!=null && !result.isEmpty()) {
            System.out.println(result.get());
            System.out.println(result.get().gettag_zhtw());
        }
        return result;
    }
}
