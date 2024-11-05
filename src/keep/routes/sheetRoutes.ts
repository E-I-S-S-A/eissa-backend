import express, { Request, Response } from "express";

const sheetRouter = express.Router();

sheetRouter.route("/add").get((req: Request, res: Response) => {
    res.send("add sheet")
});

sheetRouter.route("/delete").get((req: Request, res: Response) => {
    res.send("delete sheet")
});


export default sheetRouter;