import express, { Request, Response } from "express";

const chatRouter = express.Router();

chatRouter.route("/add").get((req: Request, res: Response) => {
    res.send("add chat")
});

chatRouter.route("/delete").get((req: Request, res: Response) => {
    res.send("delete chat")
});


export default chatRouter;