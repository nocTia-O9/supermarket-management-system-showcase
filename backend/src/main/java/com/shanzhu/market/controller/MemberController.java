package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.Member;
import com.shanzhu.market.entity.query.QueryMember;
import com.shanzhu.market.service.IMemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
@RequestMapping("/member_management/member")
public class MemberController {

    @Resource
    private IMemberService memberService;

    /*查询信息*/
    @HasPermisson("member_management:member:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryMember qo) {
        Page<Member> page = memberService.queryPageByQo(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("member_management:member:delMember")
    @PostMapping("/delMember")
    public JsonResult delMember(Long id) {
        memberService.delMember(id);
        return JsonResult.success();
    }

    @HasPermisson("member_management:member:save")
    @PostMapping("/save")
    public JsonResult save(Member member) {
        memberService.saveMember(member);
        return JsonResult.success();
    }


    @GetMapping("/queryMemberById")
    public JsonResult queryMemberById(Long id) {
        Member member = memberService.queryMemberById(id);
        return JsonResult.success(member);
    }

    @HasPermisson("member_management:member:update")
    @PostMapping("/update")
    public JsonResult updateMember(Member member) {
        memberService.updateMember(member);
        return JsonResult.success();
    }

    @GetMapping("/queryMemberByPhone")
    public JsonResult queryMemberByPhone(String phone) {
        Member member = memberService.queryMemberByPhone(phone);
        return JsonResult.success(member);
    }
}
