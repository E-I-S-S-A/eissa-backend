import express, { Request, Response } from "express";

const cellRouter = express.Router();

cellRouter.route("/add").get((req: Request, res: Response) => {
    res.send("add cell")
});

cellRouter.route("/delete").get((req: Request, res: Response) => {
    res.send("delete cell")
});


export default cellRouter;