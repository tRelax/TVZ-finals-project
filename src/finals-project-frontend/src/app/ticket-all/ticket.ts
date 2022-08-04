export interface Ticket {
    id: number;
    name: string;
    description: string;
    start_date: Date;
    due_date: Date;
    progress: number;
    priority: number;
}