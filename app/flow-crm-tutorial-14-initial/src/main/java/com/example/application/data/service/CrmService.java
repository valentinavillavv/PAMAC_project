package com.example.application.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Genere;
import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.StatusRepository;

@Service
public class CrmService {
    private final ContactRepository contactRepository;
    private final StatusRepository statusRepository;

    public CrmService(ContactRepository contactRepository,
                      StatusRepository statusRepository) { 
        this.contactRepository = contactRepository;
        this.statusRepository = statusRepository;
    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { 
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }
    
    public Contact getContact(Integer IDPAMAC){
        return contactRepository.search1(IDPAMAC);
       }
    

    public long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if (contact == null) { 
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }


    public List<com.example.application.data.entity.Status> findAllStatuses(){
        return statusRepository.findAll();
    }

    public List<Genere> findAllGender(){
        List<Genere>i=new ArrayList<Genere>();
        i.add(Genere.M);
        i.add(Genere.F);
        return i; }

    public int getGenereCount(Genere g){
        int f=0;
        int m=0;
        for(int i=0; i<countContacts();i++){
            if(contactRepository.findAll().get(i).getGenere()==Genere.M)m++;
            else{f++;}
        }
        if(g==Genere.M)return m;
        else{return f;}

    }
}