import { Request, Response } from "express";

class AuthController{
    signup(req:Request, res: Response){
        res.send("ojoj")
    }
}

export default new AuthController();