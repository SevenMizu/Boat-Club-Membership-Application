package assignment4;

import java.util.ArrayList;
import java.util.List;

class MemberRegistry {
  private List<Member> members;

  public MemberRegistry() {
    this.members = new ArrayList<>();
  }

  public void addMember(Member member) {
    members.add(member);
  }

  public void deleteMember(Member member) {
    members.remove(member);
  }

  public Member findMemberById(String id) {
    for (Member member : members) {
      if (member.getId().equals(id)) {
        return member;
      }
    }
    return null;
  }

  public List<Member> getMembers() {
    return members;
  }
}
