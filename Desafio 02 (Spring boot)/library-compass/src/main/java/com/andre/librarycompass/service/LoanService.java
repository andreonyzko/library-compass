package com.andre.librarycompass.service;

import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.repository.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanService extends AbstractService<Loan>{

    public LoanService(LoanRepository loanRepository){
        super(loanRepository);
    }
}
