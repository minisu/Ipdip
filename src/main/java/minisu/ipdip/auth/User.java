package minisu.ipdip.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.brickred.socialauth.util.AccessGrant;

import java.io.*;
import java.util.UUID;

/**
 * <p>
 * Simple representation of a User to provide the following to Resources:<br>
 * <ul>
 * <li>Storage of user state</li>
 * </ul>
 * </p>
 */
public class User {

	/**
	 * <p>
	 * Unique identifier for this entity
	 * </p>
	 */
    @JsonProperty
	private String id;

	/**
	 * <p>
	 * A username (optional for anonymity reasons)
	 * </p>
	 */
	private String userName;

	/**
	 * An OpenID identifier that is held across sessions
	 */
	private String openIDIdentifier;

	/**
	 * OAuth grant info in JSON format so we don't have to keep the SocialAuth
	 * Manager alive
	 */
	private byte oauthGrantInfo[];

	/**
	 * A shared secret between the cluster and the user's browser that is
	 * revoked when the session ends
	 */
	private UUID sessionToken;

    @JsonProperty
    private final String profileImageURL;

    private boolean anonymous;

	@JsonCreator
	public User(@JsonProperty("id") String id,
                @JsonProperty("profileImageURL") String profileImageURL) {

		this.id = id;
        this.profileImageURL = profileImageURL;
	}

    public User(String id,
                String profileImageURL,
                boolean anonymous) {

        this.id = id;
        this.profileImageURL = profileImageURL;
        this.anonymous = anonymous;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getProfileImageURL() {
        return profileImageURL;
    }

	/**
	 * @return The OpenID identifier
	 */
	public String getOpenIDIdentifier() {
		return openIDIdentifier;
	}

	public void setOpenIDIdentifier(String openIDIdentifier) {
		this.openIDIdentifier = openIDIdentifier;
	}

	public void setOAuthInfo(AccessGrant oathJSON) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(oathJSON);
		oauthGrantInfo = baos.toByteArray();
	}

	public AccessGrant getOAuthInfo() throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream bios = new ByteArrayInputStream(oauthGrantInfo);
		ObjectInputStream ois = new ObjectInputStream(bios);
		AccessGrant grant = (AccessGrant) ois.readObject();
		return grant;
	}

    @Override
    public boolean equals(Object other) {
        if(other instanceof User) {
            User that = (User) other;
            return this.getId() == that.getId();
        }
        return false;
    }

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add("id", id)
				.add("openIDIdentifier", openIDIdentifier)
				.add("sessionToken", sessionToken)
				.toString();
	}

}