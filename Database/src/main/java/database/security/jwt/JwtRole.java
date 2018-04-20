package database.security.jwt;

import net.sf.json.JSONObject;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.GrantedAuthority;
import trapx00.tagx00.entity.account.Role;

public class JwtRole implements GrantedAuthority {

    public static final JwtRole WORKER = new JwtRole(Role.WORKER.getName());
    public static final JwtRole REQUESTOR = new JwtRole(Role.REQUESTER.getName());

    private String roleName;

    private JwtRole(String roleName) {
        this.roleName = roleName;
    }

    public JwtRole(JSONObject jsonObject) {
        this.roleName = (String) jsonObject.get("name");
    }

    public JwtRole(Role role) {
        this.roleName = role.getName();
    }

    /**
     * If the <code>GrantedAuthority</code> can be represented as a <code>String</code>
     * and that <code>String</code> is sufficient in precision to be relied upon for an
     * access control decision by an {@link AccessDecisionManager} (or delegate), this
     * method should return such a <code>String</code>.
     * <p>
     * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision
     * as a <code>String</code>, <code>null</code> should be returned. Returning
     * <code>null</code> will require an <code>AccessDecisionManager</code> (or delegate)
     * to specifically support the <code>GrantedAuthority</code> implementation, so
     * returning <code>null</code> should be avoided unless actually required.
     *
     * @return a representation of the granted authority (or <code>null</code> if the
     * granted authority cannot be expressed as a <code>String</code> with sufficient
     * precision).
     */
    @Override
    public String getAuthority() {
        return roleName;
    }
}
