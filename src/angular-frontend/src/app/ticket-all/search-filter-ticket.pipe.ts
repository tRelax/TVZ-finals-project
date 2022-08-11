import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilterTicket'
})
export class SearchFilterTicketPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (!value) return null;
    if (!args) return value;

    args = args.toLowerCase();

    return value.filter(function (data) {
      return (data.name.toLowerCase().includes(args) ||
        data.id.toString().toLowerCase().includes(args) ||
        data.assignee.name.toLowerCase().includes(args) ||
        data.assignee.surname.toLowerCase().includes(args) ||
        data.tester.name.toLowerCase().includes(args) ||
        data.tester.surname.toLowerCase().includes(args) ||
        data.category.name.toLowerCase().includes(args))
    });
  }

}
