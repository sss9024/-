import AxiosService from './index'

class UserService{
    login(user){
        return AxiosService.instance.post('/user/login', {
            userId : user.userId,
            password : user.password
        }).then(response => {
            if (response.data.accessToken) {
                //console.log(response.data.accessToken)
                localStorage.setItem('user', JSON.stringify(response.data))
            }
            return response.data;
            },
            error => {
                console.log(error);
            }
        )
    }

    logout() {
        localStorage.removeItem('user');
    }

}

export default new UserService();
