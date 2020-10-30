package com.misolab.multi.site.controller;

import com.misolab.core.exception.BadRequestException;
import com.misolab.core.util.LDAPAuthenticator;
import com.misolab.core.vo.ApiResponse;
import com.misolab.multi.domain.dao.MemberDao;
import com.misolab.multi.domain.entity.Member;
import com.misolab.multi.domain.repository.MemberRepository;
import com.misolab.multi.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {

    final MemberDao memberDao;
    final MemberRepository memberRepository;

    @ResponseBody
    @GetMapping("/api")
    public ResponseEntity api(@RequestParam String userId, String name) {

        if (StringUtils.isEmpty(name)) {
            throw new BadRequestException("name is required parameter");
        }

        Member member = new Member();
        member.setUserId(userId);
        member.setName(StringUtils.isEmpty(name) ? userId : name);

        memberRepository.save(member);

        List<Member> result = memberDao.allMembers();
        List<MemberDto> list = result.stream().map(MemberDto::new).collect(Collectors.toList());

        ApiResponse response = ApiResponse.of("list", list);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public String index(Model model, String msg) {
        model.addAttribute("msg", msg);
        model.addAttribute("current", memberDao.getDate());

        return "index";
    }


    final LDAPAuthenticator ldap;

    @ResponseBody
    @GetMapping("/api/login")
    public ResponseEntity login(@RequestParam String userId, @RequestParam String password) {
//        http://localhost:8080/api/login?userId=riemann&password=password
        
//        boolean result = ldap.isSimpleLogin(userId, password);
//        ApiResponse response = ApiResponse.of("result", result);

        LDAPAuthenticator.Result result = ldap.login(userId, password);
        if (result.isOk()) {
            ApiResponse response = ApiResponse.of("result", true);
            return ResponseEntity.ok(response);
        }

        ApiResponse response = new ApiResponse();
        response.error(result.ordinal(), result.getMessage());
        return ResponseEntity.ok(response);
    }

}
