package com.ssafy.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

	@Override
	public void joinMember(MemberDto memberDto) throws Exception {
		memberMapper.joinMember(memberDto);
	}

	@Override
	public MemberDto loginMember(MemberDto memberDto) throws Exception {
		if (memberDto.getUserId() == null)
			return null;
		if (memberDto.getUserPwd() == null && memberDto.getLoginType() == 0)
			return null;
		return memberMapper.loginMember(memberDto);
	}

	@Override
	public MemberDto getMember(String userId) throws Exception {
		return memberMapper.getMember(userId);
	}

	@Override
	public void deleteMember(String userId) throws Exception {
		memberMapper.deleteMember(userId);
	}

	@Override
	public void updateMember(MemberDto memberDto) throws Exception {
		memberMapper.updateMember(memberDto);
	}

	@Override
	public List<MemberDto> listMember() throws Exception {
		return memberMapper.listMember();
	}
	
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}
	
	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}
	
	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}
	
	@Override
    public void findPassword(MemberDto memberDto) {
        
        // ?????? ????????? ?????? ArrayList ??????
        ArrayList<String> toUserList = new ArrayList<>();
        
        // ?????? ?????? ??????
        toUserList.add(memberDto.getUserEmail());
        
        // ?????? ?????? ??????
        int toUserSize = toUserList.size();
        
        // SimpleMailMessage (?????? ????????? ?????? ?????? ????????? ????????? ??? ??????)
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        
        // ????????? ??????
        simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));
        
        // ?????? ??????
        simpleMessage.setSubject("Where is my home : ???????????? ?????? ???????????????.");
        
        // ?????? ??????
        // "???????????????. Where is my home????????????.\n" + memberDto.getUserName() + "?????? ??????????????? " +memberDto.getUserPwd() + "?????????."
        simpleMessage.setText("\n\n???????????????. Where is my home????????????.\n" + memberDto.getUserName() + "?????? ??????????????? " +memberDto.getUserPwd() + "?????????.\n???????????????.\n\n");
        
        // ?????? ??????
        javaMailSender.send(simpleMessage);
    }
}
