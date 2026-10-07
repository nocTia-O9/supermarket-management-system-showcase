import {ajaxGet} from "@/assets/js/common";

const apiPrefix = '/personal'

export function queryInformation() {
    return ajaxGet(apiPrefix + "/information", {})
}
