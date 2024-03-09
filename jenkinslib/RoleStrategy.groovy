// RoleStrategy.groovy

package org.jenkinsci.plugins.rolestrategy

import com.synopsys.arc.jenkins.plugins.rolestrategy.*

class RoleStrategy {
    static void addUserToRole(String userName, String roleName, Set<String> permissions) {
        def roleStrategy = RoleBasedAuthorizationStrategy.getInstance()
        def roleMap = roleStrategy.roleMap

        def roleClass = Class.forName("com.synopsys.arc.jenkins.plugins.rolestrategy.Role")
        def role = roleMap.getRole(roleName)

        if (role == null) {
            role = roleClass.getConstructor(String, Set).newInstance(roleName, permissions)
            roleMap.addRole(role)
        }

        role.assignRole(userName)
    }
}


