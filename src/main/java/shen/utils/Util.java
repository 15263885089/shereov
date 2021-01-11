package shen.utils;



import org.springframework.security.core.context.SecurityContextHolder;
import shen.dao.User;


public class Util {
    //获取当前用户
    //Spring Security使用一个Authentication对象来描述当前用户的相关信息。SecurityContextHolder中持有的是当前用户的SecurityContext，
    // 而SecurityContext持有的是代表当前用户相关信息的Authentication的引用。
    // 这个Authentication对象不需要我们自己去创建，在与系统交互的过程中，Spring Security会自动为我们创建相应的Authentication对象，
    // 然后赋值给当前的SecurityContext。但是往往我们需要在程序中获取当前用户的相关信息，比如最常见的是获取当前登录用户的用户名。在程序的任何地方
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
        //通过Authentication.getPrincipal()可以获取到代表当前用户的信息，这个对象通常是UserDetails的实例。获取当前用户的用户名是一种比较常见的需求
    }
}
