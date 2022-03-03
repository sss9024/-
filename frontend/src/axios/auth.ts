export default function authToken() {
    const user = JSON.parse(localStorage.getItem('user'));
  
    if (user && user.accessToken) {
      return {'accessToken' : user.accessToken} ;
    } else {
      return null;
    }
}