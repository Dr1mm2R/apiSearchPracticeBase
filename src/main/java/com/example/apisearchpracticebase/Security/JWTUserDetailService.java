package com.example.apisearchpracticebase.Security;

import com.example.apisearchpracticebase.Models.PracticeManager;
import com.example.apisearchpracticebase.Models.Student;
import com.example.apisearchpracticebase.Repositories.PracticeManagerRepos;
import com.example.apisearchpracticebase.Repositories.StudentRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class JWTUserDetailService implements UserDetailsService {
    @Autowired
    StudentRepos studentRepos;
    @Autowired
    PracticeManagerRepos practiceManagerRepos;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Random random = new Random();
        Student student = new Student();
        PracticeManager practiceManager = new PracticeManager();

        JWTUser jwtUser = null;
        if(studentRepos.findByStudentLogin(username).isPresent()){
            student = studentRepos.findByStudentLogin(username).get();
            List<String> roles = Arrays.asList("STUDENT");
            jwtUser = new JWTUser(student.getId(), student.getStudentLogin(), random.nextInt(99999) + "", roles);
        }
        if(practiceManagerRepos.findByManagerLogin(username).isPresent()){
            practiceManager = practiceManagerRepos.findByManagerLogin(username).get();
            List<String> roles = Arrays.asList("PRACTICEMANAGER");
            jwtUser = new JWTUser(practiceManager.getId(), practiceManager.getManagerLogin(), random.nextInt(99999) + "", roles);
        }

        return jwtUser;
    }
}
