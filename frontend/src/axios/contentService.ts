import AxiosService from "./index"
import authToken from "./auth"
import { auth } from "@/store/authModule"

class ContentService {
    getMainUnit(mainId){
        return AxiosService.instance.get(`/mainUnit/${mainId}`, {headers: authToken()})
    }

    getMainUnitList(bookId){
        return AxiosService.instance.get(`/mainUnit/list/${bookId}`, {headers: authToken()})
    }

    getMainUnitTitle(mainId){
        return AxiosService.instance.get(`/mainUnit/title/${mainId}`, {headers: authToken()})
    }

    getPrimaryUnitList(mainId){
        return AxiosService.instance.get(`/primaryUnit/${mainId}`, {headers: authToken()})
    }

    getPrimaryUnitTitle(primaryId){
        return AxiosService.instance.get(`/primaryUnit/title/${primaryId}`, {headers: authToken()})
    }

    getSubUnitList(primaryId){
        return AxiosService.instance.get(`/subUnit/${primaryId}`, {headers: authToken() })
    }

    getSubUnitTitle(subId){
        return AxiosService.instance.get(`/subUnit/title/${subId}`, {headers: authToken() })
    }

    getDetail(detailId){
        return AxiosService.instance.get(`/detail/${detailId}`, {headers: authToken() })
    }

    getDetailList(subId){
        return AxiosService.instance.get(`/detail/list/${subId}`, {headers : authToken() })
    }

    getDetailTitle(detailId){
        return AxiosService.instance.get(`/detail/title/${detailId}`, {headers: authToken()})
    }

    getBook(user){
        return AxiosService.instance.post(`/book`, {
           grade : user.userGrade,
           school : user.userSchool
        }, {headers: authToken()})
    }




}

export default new ContentService();