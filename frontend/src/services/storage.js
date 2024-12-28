const TOKEN_KEY = "access_token";
const USER_ID = "user_id";
const USER_ROLE_ID = "user_role_id";

export const saveToken = (token) => {
  localStorage.setItem(TOKEN_KEY, token);
};

export const loadToken = () => {
  return localStorage.getItem(TOKEN_KEY);
};

export const clearToken = () => {
  localStorage.removeItem(TOKEN_KEY);
};

export const isAuthenticated = () => {
  if (!loadToken()) {
    return false;
  }
  return true;
}

export const decodeToken = (token) => {
  try {
    const decoded = jwtDecode(token);
    return decoded;
  } catch (error) {
    console.error('Invalid token:', error);
    return null;
  }
}

export const getAuthHeader = () => {
  const token = loadToken();
  if (token) {
    return { Authorization: `Bearer ${token}` };
  }
  return {};
}

export const saveUserId = (id) => {
  localStorage.setItem(USER_ID, id);
};

export const loadUserId = () => {
  const id = localStorage.getItem(USER_ID);
  return id ? parseInt(id, 10) : null;
}

export const clearUserId = () => {
  localStorage.removeItem(USER_ID);
}

export const saveUserRoleId = (id) => {
  localStorage.setItem(USER_ROLE_ID, id);
}

export const loadUserRoleId = () => {
  return localStorage.getItem(USER_ROLE_ID);
}

export const clearUserRoleId = () => {
  localStorage.removeItem(USER_ROLE_ID);
}