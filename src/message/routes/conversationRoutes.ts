import express, { Request, Response } from "express";

const conversationRouter = express.Router();

conversationRouter.route("/add").get((req: Request, res: Response) => {
    res.send("add conversation")
});

conversationRouter.route("/delete").get((req: Request, res: Response) => {
    res.send("delete conversation")
});

export default conversationRouter;